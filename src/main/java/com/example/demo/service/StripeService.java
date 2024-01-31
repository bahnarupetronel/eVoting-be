package com.example.demo.service;

import com.example.demo.configs.StripeConfig;
import com.example.demo.model.StripeSession;
import com.example.demo.model.User;
import com.example.demo.repository.StripeSessionRepository;
import com.example.demo.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.identity.VerificationSession;
import com.stripe.param.identity.VerificationSessionCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.glue.model.EntityNotFoundException;

@Service
public class StripeService {
    @Autowired
    private StripeConfig stripeConfig;
    @Autowired
    private StripeSessionRepository stripeSessionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public String createVerificationSession(HttpServletRequest request) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();
        User user = userService.getUser(request);

        VerificationSession verificationSession = getStripeVerificationSession(user);

        if(verificationSession != null)
            return verificationSession.toJson();

        VerificationSessionCreateParams params = createSessionParams();

        verificationSession =  VerificationSession.create(params);
        StripeSession newStripeSession = new StripeSession(verificationSession.getId(), user);
        stripeSessionRepository.save(newStripeSession);
        return verificationSession.toJson();
    }

    public String getVerificationSession(HttpServletRequest request) throws StripeException {
        Stripe.apiKey = stripeConfig.getSecretKey();
        User user = userService.getUser(request);

        VerificationSession verificationSession = getStripeVerificationSession(user);
        System.out.println(verificationSession.getStatus());

        if(verificationSession.getStatus().equals("verified")){
            user.setIsIdentityVerified(true);
            userRepository.save(user);
        }
        return verificationSession.toJson();
    }

    private VerificationSessionCreateParams createSessionParams() {
        return VerificationSessionCreateParams.builder().setType(VerificationSessionCreateParams.Type.DOCUMENT)  .setOptions(
                        VerificationSessionCreateParams.Options.builder()
                                .setDocument(
                                        VerificationSessionCreateParams.Options.Document.builder()
                                                .setRequireMatchingSelfie(true)
                                                .build()
                                )
                                .build()
                ).setReturnUrl("http://127.0.0.1:3000/user/validate/identity/stripe").build();
    }

    private VerificationSession getStripeVerificationSession(User user) throws StripeException {
        if(user.getIsIdentityVerified()){
            throw new EntityNotFoundException(("This action can't be completed"));
        }
        StripeSession stripeSession = stripeSessionRepository.findByUser(user);

        if(stripeSession != null)
              return VerificationSession.retrieve(stripeSession.getSessionToken());
        return null;
    }
}
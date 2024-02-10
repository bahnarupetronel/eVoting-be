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
import com.stripe.param.identity.VerificationSessionRetrieveParams;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.glue.model.EntityNotFoundException;

import javax.naming.InvalidNameException;

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

    public String createVerificationSession(HttpServletRequest request) throws StripeException, InvalidNameException {
        Stripe.apiKey = stripeConfig.getSecretKey();
        User user = userService.getUser(request);

        VerificationSession verificationSession = getStripeVerificationSession(user);
        System.out.println("hello1");
        if(verificationSession != null){
            System.out.println("hello, session exists");
            isDocumentMatching(verificationSession, user);
            return verificationSession.toJson();
        }
        System.out.println("hello,  session doesnt exists");
        VerificationSessionCreateParams params = createSessionParams();

        verificationSession =  VerificationSession.create(params);
        StripeSession newStripeSession = new StripeSession(verificationSession.getId(), user);
        stripeSessionRepository.save(newStripeSession);
        return verificationSession.toJson();
    }

    public String getVerificationSession(HttpServletRequest request) throws StripeException, InvalidNameException {
        Stripe.apiKey = stripeConfig.getSecretKey();
        User user = userService.getUser(request);
        VerificationSession verificationSession = getStripeVerificationSession(user);
        isDocumentMatching(verificationSession, user);
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
        VerificationSessionRetrieveParams params = VerificationSessionRetrieveParams.builder()
                .addExpand("verified_outputs")
                .build();

        VerificationSession verificationSession = null;
        if(stripeSession != null){
            return VerificationSession.retrieve(stripeSession.getSessionToken(), params, null);
        }
        return null;
    }

    private void isDocumentMatching(VerificationSession verificationSession, User user) throws InvalidNameException {
        if(verificationSession.getStatus().equals("verified")){
            String firstName = verificationSession.getVerifiedOutputs().getFirstName().toLowerCase();
            String lastName = verificationSession.getVerifiedOutputs().getLastName().toLowerCase();
            String firstNameUser = user.getFirstName().toLowerCase();
            String lastNameUser = user.getLastName().toLowerCase();

            if(firstName.equals(firstNameUser) && lastName.equals(lastNameUser)){
                user.setIsIdentityVerified(true);
                user.setStripeSession(null);
                userRepository.save(user);
                StripeSession stripeSession = stripeSessionRepository.findByUser(user);
                stripeSessionRepository.deleteById(stripeSession.getSessionId());
            }
            else {
                StripeSession stripeSession = stripeSessionRepository.findByUser(user);
                stripeSessionRepository.deleteById(stripeSession.getSessionId());
                throw new InvalidNameException("Datele personale din cont nu se potrivesc cu cele datele de pe cartea de identitate.");
            }
        }
    }
}
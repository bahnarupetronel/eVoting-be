pragma solidity >=0.6.12 <0.9.0;

library SafeMath {
    function mul(uint a, uint b) internal pure returns (uint) {
        if (a == 0) {
            return 0;
        }
        uint c = a * b;
        require(c / a == b, "SafeMath: multiplication overflow");
        return c;
    }

    function div(uint a, uint b) internal pure returns (uint) {
        require(b > 0, "SafeMath: division by zero");
        uint c = a / b;
        return c;
    }
}

contract TransactionFee {
    using SafeMath for uint;

    uint public fee;
    address payable public receiver;
    mapping (address => uint) public balances;

    event Sent(address from, address to, uint amount, bool sent);

    constructor(address payable _receiver, uint _fee) {
        receiver = _receiver;
        fee = _fee;
    }

    function getReceiverBalance() public view returns(uint) {
        return receiver.balance;
    }

    function sendTrx() public payable {
        require(msg.value > 0, "Value must be greater than zero");

        uint value = msg.value.mul(fee) / 100;
        require(value <= receiver.balance, "Insufficient balance to transfer");

        (bool sent, ) = receiver.call{value: value}("");
        require(sent, "Failed to send Ether");

        balances[receiver]  = balances[receiver] +value;
        emit Sent(msg.sender, receiver, value, sent);
    }
}
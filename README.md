In this project, you can manage your employees using the Ethereum blockchain.

To get started, you need to locate the "SMART_CONTRACT.sol" file in the resources folder and deploy it on an Ethereum network.

Next, open the "Web3Config" file (located in the configuration package) and add your own Ethereum network API key. This key allows you to communicate with the blockchain. You can obtain an API key by creating your own local node or by using a service like alchemy.com. Additionally, paste the private key of your wallet into the configuration file. Make sure to use the private key associated with the smart contract you created, as adding new employees is a function reserved for the contract creator. Finally, add the contract address to the "CONTRACT_ADDRESS" variable. You will receive this address after successfully creating the smart contract.

To add a new employee, use the following JSON format:

{
"firstName": "John",
"lastName": "Cena",
"position": "Boss"
}
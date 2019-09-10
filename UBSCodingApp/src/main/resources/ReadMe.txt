//ReadMe

App.java is the main Class

Step 1: Persist the Dummy Trade Object into  H2 Database
Step 2: Read the Trade Object from H2 Database using JPA and publish them
Step 3: There is publisher Subscriber Model build using LinkedTransferQueue
Step 4: Publisher will publish the Trade and Subscriber will consume the Trade.There is enrichment of totalExecutedAmount which is transient variable (price * quantity)
Step 5: Build the AggregatedRecord Object based on Client Code and its associated Stock Object and publish them once consume.

Note:
1) Instead of doing actual publishing I am just printing the hashmap for testing purpose and also not removing elements after publishing to make result simple to validate
2)
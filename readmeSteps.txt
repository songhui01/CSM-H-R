1)Mocking data generation
context.utility.randomGenerator.java
The file is samplefile1.txt

2)convert from text to json
context.convertion.ConvertFileToClassToJson
The file is samplefile2.json

3)from json to CASM
The main functions are in "EntryForList.java" and "EntryLoadandUpdateForOneDomain.java"
The first is for casm generation for a list and the second is for updating casm for one entry
The file is under the datafiles folder.


1)for ssm, sitational state machines. The time can be selected that whether the half an hour is a boundary.
the situational state machine is maintained in the entity of elevator,and the state are the number in each casm

2)The cam engine can have a reflection module, which can be used to show the relationship of entities, and the transition weights.

TODO:
1) Add person list under person category, and give each person an index, so the person can be used to form a matrix.

In our framework, it would be context object list under a context category. These objects can be persons or other devices, and these objects 
can have relationships between them. How to identify an object, we use category URI and object URI, or category id and object id.

2) Relationship Registration; We need to create the related relations, and in a real world, the relations can be read from
other systems such as the management systems of a university. We use the created relations to register in the context domain. 

3) If we want to make a decision for a person, we can build a situation state machine automatically based on the above information. 
The SSM can be used to make predictions. We can find the most steady situation or the most effective situation which can affect the decision
making of a person, which could be future work. 

4) Using the same way as CASM, we give each situation an index, and we have the comparison of situation function, if a new situation is the
same to an old situation, then this situation may 


It does not need to have a database using our engine. Our engine can be the base of a lightweight platform to be fast deployed in any sensor device.


For user who configure the CSM to do
1) design the domain, set the category, may set the relations 
2) write their own assemble casm or assemble cssm

our framework do:
1)give the general framework which can work
2)has extensibility
# CSM-H-R
A Context Modeling Framework in Supporting Reasoning Automation for Interoperable Intelligent Systems and Privacy Protection.

# Steps to run an example
1) According to different input formats selected (Triple-RDF or Triple-HR), DomainInitialization.java or DomainInitializationHR.java can be run to initialize the domain
and the context category information. In this implementation, we mannually initialize the categories for testing purpose.
2) BoomWithTripleListCSSM.java or BoomWithTripleListCASMhr.java can be run to build CSSM an CASM for mocked data from config/samplefile(X).json.
3) After building a CSM file, such as casmfile_domain_4.json, VariousProbabilitiesUtil.java can be run to obtain a naive decision probability.

# Steps to generate data for Triple-RDF
1) context.utility.randomGenerator.java is targeting a file like samplefile1.txt.
2) convert from text to json: context.convertion.ConvertFileToClassToJson targeting a file like samplefile2.json.


# Steps to generate data for Triple-HR
PersonActivityGenerator.java targeting a file like samplefile2.json.
 
A detailed version of the CSM-H-R class diagram
<img src="https://github.com/songhui01/CSM-H-R/assets/12132911/1e159c83-f370-4985-8259-0507f13b64f9" alt="Image" width="800" height="800">




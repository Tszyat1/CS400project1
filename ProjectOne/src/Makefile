JC =javac
JFLAGS = -g
J=java

#File path
FILE_PATH = ./movies.csv
#Defining whole data files of .java
DATA_JAVAS = MovieInterface.java MovieDataReaderInterface.java MovieDataReader.java Movie.java
#Defining whole HashTable files of .java
HASH_MAP_TABLE_MAP = HashTableMap.java
MAP_ADT=MapADT.java
#Defining whole BackEnd files of .java
BACK_END_JAVAS = BackendInterface.java Backend.java
#Defining whole FrontEnd files of .java
FRONT_END_JAVAS = Frontend.java 

DATA_CLASSES = MovieInterface.class MovieDataReaderInterface.class MovieDataReader.class Movie.class
FRONT_END_CLASSES = Frontend.class 
HASH_TABLE_CLASSES = MapADT.class HashTableMap.class
BACK_END_CLASSES = BackendInterface.class Backend.class




run: Frontend.class
	@echo "Runing Program"
	$(J) Frontend $(FILE_PATH)

compile: compileFrontEndJavas
	@echo "Compileing Back End"

compileDataJavas: $(DATA_JAVAS)
	@echo "Compileing Data"
	$(JC) $(JFLAGS) $(DATA_JAVAS)

compileMapADT: $(MAP_ADT)
	@echo "Compileing Map ADT"
	$(JC) $(JFLAGS) $(MAP_ADT)	
	
compileHashTableJavas: $(HASH_MAP_TABLE_MAP) compileMapADT
	@echo "compileing Hash Table Map"
	$(JC) $(JFLAGS) $(HASH_MAP_TABLE_MAP)

compileBackEndJavas: $(BACK_END_JAVAS) compileHashTableJavas compileDataJavas
	@echo "Compileing Back End"
	$(JC) $(JFLAGS) $(BACK_END_JAVAS)

compileFrontEndJavas: $(FRONT_END_JAVAS) compileBackEndJavas
	@echo "Compileing Front End"
	$(JC) $(JFLAGS) $(FRONT_END_JAVAS)


test: testFrontend testBackend testData
	

testFrontend: $(DATA_CLASSES) $(FRONT_END_CLASSES) $(HASH_TABLE_CLASSES) $(BACK_END_CLASSES)
	@echo "Testing Front End"
	$(JC) $(JFLAGS) TestFrontend.java
	$(J) TestFrontend $(FILE_PATH)

	
testBackend: $(DATA_CLASSES) $(FRONT_END_CLASSES) $(HASH_TABLE_CLASSES) $(BACK_END_CLASSES)
	@echo "Testing Front End"
	$(JC) $(JFLAGS) TestBackend.java
	$(J) TestBackend 
	
	
testData: $(DATA_CLASSES) $(FRONT_END_CLASSES) $(HASH_TABLE_CLASSES) $(BACK_END_CLASSES)
	@echo "Testing data"
	$(JC) $(JFLAGS) TestMovieAndMovieDataReader.java
	$(J) TestMovieAndMovieDataReader

clean:
	$(RM) *.class

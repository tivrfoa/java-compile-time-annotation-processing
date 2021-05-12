## Java Annotation Processor

I'm just trying to learn how to use it from the command line.

I couldn't find useful information from the Oracle website.<br>
So my approach was to look at how MapStruct implements it:

https://github.com/mapstruct/mapstruct/blob/master/processor/src/main/java/org/mapstruct/ap/MappingProcessor.java

## What is the default annotation processors discovery process?

> The default way to make an annotation processor available to the compiler is to register it in a file in META-INF/services/javax.annotation.processing.Processor. The file can contain a number of processors: each the fully-qualified class name on its own line, with a newline at the end. The compiler will default to using processors found in this way if none are specified.

  - [answered Jul 27 '12 at 10:04 Daniel](https://stackoverflow.com/a/11685610/339561)

## Compiling

```sh
$ javac main.java
GetProcessor#init
GetProcessor#getSupportedSourceVersion
```

```sh
$ javac Person.java
GetProcessor#init
GetProcessor#getSupportedSourceVersion
GetProcessor#process
processingOver false
GetProcessor#process
processingOver true
```

## ANNOTATION PROCESSING

https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/javac.html#processing

javac provides direct support for annotation processing, superseding the need for the separate annotation processing tool, apt.

The API for annotation processors is defined in the javax.annotation.processing and javax.lang.model packages and subpackages.
Overview of annotation processing

Unless annotation processing is disabled with the -proc:none option, the compiler searches for any annotation processors that are available. The search path can be specified with the -processorpath option; if it is not given, the user class path is used. Processors are located by means of service provider-configuration files named META-INF/services/javax.annotation.processing.Processor on the search path. Such files should contain the names of any annotation processors to be used, listed one per line. Alternatively, processors can be specified explicitly, using the -processor option.

After scanning the source files and classes on the command line to determine what annotations are present, the compiler queries the processors to determine what annotations they process. When a match is found, the processor will be invoked. A processor may "claim" the annotations it processes, in which case no further attempt is made to find any processors for those annotations. Once all annotations have been claimed, the compiler does not look for additional processors.

If any processors generate any new source files, another round of annotation processing will occur: any newly generated source files will be scanned, and the annotations processed as before. Any processors invoked on previous rounds will also be invoked on all subsequent rounds. This continues until no new source files are generated.

After a round occurs where no new source files are generated, the annotation processors will be invoked one last time, to give them a chance to complete any work they may need to do. Finally, unless the -proc:only option is used, the compiler will compile the original and all the generated source files.

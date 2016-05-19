#Sperimentare con la PROXY REFLECTION:

1) Creare una classe fittizia che implementa un'interfaccia
2) Creare un proxy di tale classe che faccia il "tracing" ovvero tenga un contatore di quante volte il suo metodo fittizio è stato chiamato (e chi lo ha chiamato?)

##Motivazioni
*(pag 77 di reflection in action) including tracing code and guards to turn it on and off in each class bloats the classes and makes them slower because of the execution of the if statements. Due to these constraints, George decides to make tracing and nontracing versions of his classes.
*
*
*

#Osservazioni:
* l'oggetto proxy può invocare solo i metodi definiti nelle interfacce implementate dalla classe target e passate al metodo get proxy
	
* Dal debug vediamo che la variable ProxedSubject contiene un field chiamato h contentente l'istanza del "TargetInvocationHandler" passata al metodo getProxy (lo dice anche nelle api! https://docs.oracle.com/javase/7/docs/api/)

* Se ispezioniamo la variabile ProxedSubject troviamo altre cose:
	* in accordo con il debug il getClass applicato a proxedSubject restituisce la classe creata a runtime $Proxy0
	* il get fields applicato a tale class non restituisce l'attributo h ma 4 metodi:
		* private static java.lang.reflect.Method m_i 	risulanti essere dichiarati dalla classe $Proxy0
	* ispezionando l'istanza vediamo che questi metodi sono
		* java.lang.Object.toString()
		* java.lang.Object.equals( Object o)
		* java.lang.Object.hashCode()
		* it.csttech.demoproxy.TargetInterface.method()
	* ovvero 3 sono metodi ereditati da object mentre l'ultimo è quello definito della mia interfaccia
		* anche i doc ci dicono che quei 3 metodi di object si comportano esattamente come i metodi che fanno parte dell'interfaccia di creazione del proxy (quindi vengono ingabbiati nella chiamata dell'invocation handler)
			* _"An invocation of the hashCode, equals, or toString methods declared in java.lang.Object on a proxy instance will be encoded and dispatched to the invocation handler's invoke method in the same manner as interface method invocations are encoded and dispatched, as described above. The declaring class of the Method object passed to invoke will be java.lang.Object. Other public methods of a proxy instance inherited from java.lang.Object are not overridden by a proxy class, so invocations of those methods behave like they do for instances of java.lang.Object."_
		* applicando il getMethods alla classe $Proxy0 troviamo che i 4 metodi suddetti li ha definiti con il modificatore "public final" e risultano dichiarati direttamente da essa e non  ereditati!
		* siccome $Proxy0 è classe figlia di Proxy vediamo che correttamente risultano ereditati i metodi non private di Proxy e i metodi di Object
		* il field h di Proxy è definito protected eppure non appare.. Il motivo è che ho due metodi il get... e il getDeclared.
			* il primo lista tutti gli oggetti public della classe corrente compresi gli ereditati
			* il secondo lista tutti gli oggetti definiti all'interno della classe indipendentemente da quale sia il modificatore.

*


Sperimentare con la PROXY REFLECTION:

	1) Creare una classe fittizia che implementa un'interfaccia
	2) Creare un proxy di tale classe che faccia il "tracing" ovvero tenga un contatore di quante volte il suo metodo fittizio è stato chiamato (e chi lo ha chiamato?)

		(pag 77 di reflection in action)
		including tracing code and guards to turn it on and off in each class bloats the classes and makes them slower because of the execution of the if statements. Due to these constraints, George decides to make tracing and nontracing versions of his classes.

	*
	*
	*

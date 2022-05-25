# M15_JocDeDaus
API creada con el framework Spring que forma parte del itinerario para completar el bootcamp online de la IT Academy - Barcelona Activa.
Los requerimientos para superar este módulo eran los siguientes:
- Se juega con dos dados. Si el resultado es 7, la tirada es ganadora, sino cuenta como perderdora.
- Para poder jugar un usuario debe registrarse. Al crearse se le asigna un identificador numérico único y una fecha de registro. Si el usuario lo desea, puede registrarse sin nombre y se llamará "ANÒNIM". Puede haber varios jugadores anónimos, pero ningún nombre puede ser repetido.
- Cada jugador puede ver el listado de todas las tiradas que ha hecho y su porcentaje de éxito, por cada tirada puede ver el valor de cada dado i se es ganadora o perdedora.
- No se puede eliminar una tirada en concreto, pero si se pueden eliminar todas las tiradas de un jugador.
- El software debe permitir listar todos los jugadores y el porcentaje de éxito de cada uno.
- Lo principales patrones de diseño deben ser respetados.
- URLs:
  - POST: /players : crea un jugador 
  - PUT /players : modifica el nombre del jugador 
  - POST /players/{id}/games/ : un jugador específico realiza una tirada de dados.  
  - DELETE /players/{id}/games: elimina las tiradas del jugador 
  - GET /players/: retorna el listado de todos los jugadores del sistema con su porcentaje de éxito
  - GET /players/{id}/games: retorna el listado de jugadas de un jugador
  - GET /players/ranking: retorna una lista con los jugadores ordenados por ranking
  - GET /players/ranking/loser: retorna el jugador con peor ranking 
  - GET /players/ranking/winner: retorna el jugador con mejor ranking
  
Para superar el ejercicio se deben superar las 3 siguientes fases:
- FASE 1
  - Utilizar MySQL como base de datos
- FASE 2
  - Cambiar la configuración y utilizar MongoDB para persistir los datos
- FASE 3
  - Añadir seguridad: incluye autenticación JWT en todos los accesos a las URL


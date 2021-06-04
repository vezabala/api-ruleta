# api-ruleta
### 1. Endpoint de creación de nuevas ruletas que devuelva el id de la nueva ruleta creada
## Respuesta:
con el endpoint  
### [POST] host:puerto/roulette
## RESULTADO:
```
{
    "status": 201,
    "ok": true,
    "error": null,
    "message": "446af30d3c5b4fcab1c076d15fb2a4e9",
    "content": null
}
```
### 2. Endpoint de apertura de ruleta (el input es un id de ruleta) que permita las posteriores peticiones de apuestas, este debe devolver simplemente un estado que confirme que la operación fue exitosa o denegada.
## Respuesta:
con el endpoint 
### [GET] host:puerto/roulette/opening/446af30d3c5b4fcab1c076d15fb2a4e9
## RESULTADO:
```
{
    "status": 200,
    "ok": true,
    "error": null,
    "message": "Se ha realizado correctamente la apertura de la ruleta",
    "content": {
        "id": "446af30d3c5b4fcab1c076d15fb2a4e9",
        "status": true,
        "bet": []
    }
}
```
### 3. Endpoint de apuesta a un número (los números válidos para apostar son del 0 al 36) o color (negro o rojo) de la ruleta una cantidad determinada de dinero (máximo 10.000 dólares) a una ruleta abierta. nota: este enpoint recibe además de los parámetros de la apuesta, un id de usuario en los HEADERS asumiendo que el servicio que haga la petición ya realizo una autenticación y validación de que el cliente tiene el crédito necesario para realizar la apuesta.
## Respuesta:
primero se crea el usuario con el endpoint
### [POST] host:puerto/user/
## [BODY]:
```
{"name":"Esneyder Zabala", "balance": 33}
```
## RESULTADO:
```
[
  {
      "status": 201,
      "ok": true,
      "error": null,
      "message": "a4307d9f2a7a4341a6821ffe31dd38a7",
      "content": null
  }
]
```
para consultarlo es con el endpoint
### [GET] host:puerto/user/
## RESULTADO:
```
[
   {
      "id": "a4307d9f2a7a4341a6821ffe31dd38a7",
      "name": "Esneyder Zabala",
      "balance": 33.0
   }
]
```
Luego para realizar la apuesta se usa el siguiente endpoint teniendo cuenta el id de la ruleta
### [GET] host:puerto/roulette/bet/446af30d3c5b4fcab1c076d15fb2a4e9
En el header colocar el id del usuario que va apostar con esa ruleta
## [HEADERS]:
```
user-id a4307d9f2a7a4341a6821ffe31dd38a7
```
en el body poner la apuesta
## [BODY]:
```
{"money":15, "number":25}
{"money":15, "color":"rojo"} {"money":15, "color":"negro"}
```
## RESULTADO:
```
{
    "status": 200,
    "ok": true,
    "error": null,
    "message": "Se ha realizado correctamente la apuesta",
    "content": {
        "id": "446af30d3c5b4fcab1c076d15fb2a4e9",
        "status": true,
        "bet": [
            {
                "value": "25",
                "resultValue": "25",
                "cash": 15.0,
                "user": {
                    "id": "a4307d9f2a7a4341a6821ffe31dd38a7",
                    "name": "Esneyder Zabala",
                    "balance": 48.0
                }
            }
        ]
    }
}
```
### 4. Endpoint de cierre apuestas dado un id de ruleta, este endpoint debe devolver el resultado de las apuestas hechas desde su apertura hasta el cierre. El número ganador se debe seleccionar automáticamente por la aplicación al cerrar la ruleta y para las apuestas de tipo numérico se debe entregar 5 veces el dinero apostado si atinan al número ganador, para las apuestas de color se debe entrega 1.8 veces el dinero apostado, todos los demás perderán el dinero apostado. nota: para seleccionar el color ganador se debe tener en cuenta que los números pares son rojos y los impares son negros.
con el endpoint:
### [GET] host:puerto/roulette/closing/446af30d3c5b4fcab1c076d15fb2a4e9
## RESULTADO:
```
{
    "status": 200,
    "ok": true,
    "error": null,
    "message": "Se ha realizado correctamente el cierre de la ruleta",
    "content": {
        "id": "446af30d3c5b4fcab1c076d15fb2a4e9",
        "status": false,
        "bet": [
            {
                "value": "25",
                "resultValue": "25",
                "cash": 15.0,
                "user": {
                    "id": "a4307d9f2a7a4341a6821ffe31dd38a7",
                    "name": "Esneyder Zabala",
                    "balance": 48.0
                }
            }
        ]
    }
}
```
### 5. Endpoint de listado de ruletas creadas con sus estados (abierta o cerrada)
con el endpoint:
### [GET] host:puerto/roulette
## RESULTADO:
```
[
   {
        "id": "446af30d3c5b4fcab1c076d15fb2a4e9",
        "status": false,
        "bet": [
            {
                "value": "25",
                "resultValue": "25",
                "cash": 15.0,
                "user": {
                    "id": "a4307d9f2a7a4341a6821ffe31dd38a7",
                    "name": "Esneyder Zabala",
                    "balance": 48.0
                }
            }
        ]
    }
]
```

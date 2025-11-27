export const Util = Object({
  //v1: 'https://testsw.pj.gob.pe:72/prueba-api/', //api rest
  //"v1": "http://172.19.9.35:8080/prueba-api/",//api rest
  //"v1": "http://172.18.13.173:8193/prueba-api/",//api rest
  //v1: 'http://172.34.12.71:8083/votacion-judicial-suprema-api-rest/',
  v1: 'http://172.19.9.35:8080/votacion-judicial-suprema-api-rest/',
  v2: '6LeqbAAnAAAAAIfjfpyKd8J1k5tHZa6uFClrLmxm', //token captcha
  v3: 'XWEwuS5kx9HRSSKqkgQnag==', //codigo rol
  v4: '/gYu2N0KWEABKAGXXXTjEw==', //usuario consumo
  v5: 'ndAUiY58oUA=', // clave usuario consumo
});
/*
CanActivate, CanActivateChild // validacion token, token nivel, validacion de urls permitidas con rol
Resolve, // token auth (basico)
CanLoad -> lazyloading : validacion por modulos validacion token,  TOKEN NIVEL
*/
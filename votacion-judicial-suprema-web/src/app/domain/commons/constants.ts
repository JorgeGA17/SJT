// Se codifica según proyecto
export const constantes = {
    JWT_TOKEN: 'VJ_TOKEN',
    JWT_TOKEN_NIVEL: 'VJTK_NVL',
    TOKEN_VALID_SEC: 'VJ_EXP',
    REFRESH_TOKEN_VALID_SEC: 'VJ_REF',
    DATETIME_NEW_TOKEN: 'VJ_TNT',
    USUARIO: 'USR_VJS',
    USUARIO_OPCIONES: 'USR_VJS_OP',
    USUARIO_PERFIL: 'USR_VJS_PE',
    USUARIO_SALA: 'USR_VJS_SALA',
    RES_COD_EXITO: '0000',
    RES_COD_NO_DATA: '-1', //0000
};

export const tokenNiveles ={
  NIVEL_AUTH:'VJ_TOKEN_BASIC',
  NIVEL_LOGIN: 'VJ_TOKEN_LOGIN',
  NIVEL_OPCIONES: 'VJ_TOKEN_OPCIONES_PERFIL'
}

export const urlsGlobal = ['/autenticacion'];

export const mensajes = {
  MSG_RESP_NO_DATA: 'No se encontraron datos en la respuesta',
  MSG_RESP_NO_DATA_LIST: 'No se encontraron resultados',
};
  
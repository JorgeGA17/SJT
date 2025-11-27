import * as states from './general.states';
//import * as statesLayout from './layout.states';
//import * as statesAdmin from './admin.states';

export const mostrartituloNavBarInit: states.mostrartituloNavBar = {
    titulo: ""
};
export const mostrarCargandoInit: states.mostrarCargando = {
    estado: false
};
export const seleccionarOpcionMenuInit: states.seleccionarOpcionMenu= {
    url:""
}
export const seleccionarOpcionMenuIndiceInit: states.seleccionarOpcionMenuIndice = {
    indice:-1
}

/* export const recuperarUsuarioInit: states.recuperarUsuario = {
    usuario: null
} 
export const seleccionarOpcionMenuInit: statesLayout.seleccionarOpcionMenu= {
    url:""
}
export const seleccionarOpcionMenuIndiceInit: statesLayout.seleccionarOpcionMenuIndice = {
    indice:-1
}
export const cargarConferenciaDetalleInit: statesAdmin.cargarConferenciaDetalle= {
    detalle:null
};*/
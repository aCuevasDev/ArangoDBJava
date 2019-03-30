/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-03-30 16:28:25.

export interface DepartamentoDTO extends IKeyable {
    nombre: string;
    jefe: string;
}

export interface EmpleadoDTO extends IKeyable {
    nombre: string;
    apellidos: string;
    username: string;
    contrasenya: string;
    jefe: boolean;
    departamento: string;
    nombreCompleto: string;
}

export interface EventoDTO extends IKeyable {
    tipo: Tipo;
    fecha: Date;
    empleado: string;
}

export interface IncidenciaDTO extends IKeyable {
    origen: string;
    destino: string;
    titulo: string;
    descripcion: string;
    fechaInicio: Date;
    fechaFin: Date;
    urgente: boolean;
}

export interface RankingDTO extends Comparable<RankingDTO> {
    nombre: string;
    incidenciasResueltas: number;
}

export interface IKeyable {
    key: string;
}

export interface Comparable<T> {
}

export type Tipo = "LOGIN" | "CREACION_INCIDENCIA" | "CONSULTA_INCIDENCIA" | "SOLUCION_INCIDENCIA";

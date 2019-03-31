/* tslint:disable */
// Generated using typescript-generator version 2.12.476 on 2019-03-31 19:05:13.

export interface DepartamentoDTO extends ArangoDocument {
    nombre: string;
    jefe: string;
}

export interface EmpleadoDTO extends ArangoDocument {
    nombre: string;
    apellidos: string;
    username: string;
    contrasenya: string;
    jefe: boolean;
    departamento: string;
    nombreCompleto: string;
}

export interface EventoDTO extends ArangoDocument, Comparable<EventoDTO> {
    tipo: Tipo;
    fecha: Date;
    empleado: string;
}

export interface IncidenciaDTO extends ArangoDocument {
    id: string;
    origen: string;
    destino: string;
    titulo: string;
    descripcion: string;
    fechaInicio: Date;
    fechaFin: Date;
    urgente: boolean;
    solucionada: boolean;
}

export interface RankingEntryDTO extends Comparable<RankingEntryDTO> {
    nombre: string;
    incidenciasResueltas: number;
}

export interface ArangoDocument {
    key: string;
    collection: string;
}

export interface Comparable<T> {
}

export type Tipo = "LOGIN" | "CREACION_INCIDENCIA" | "SOLUCION_INCIDENCIA";

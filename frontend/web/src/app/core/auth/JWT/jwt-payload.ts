export interface JWTPayload {
    sub: string
    iat: number
    
    name: string
    surname: string
    role: string
}

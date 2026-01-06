import { TUserRole } from "../../../modules/profile/user/user"

export interface JWTPayload {
    sub: string
    iat: number
    
    name: string
    surname: string
    email: string
    password: string
    role: TUserRole
}

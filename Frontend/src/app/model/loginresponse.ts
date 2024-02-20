export class LoginResponse {

    jwtToken: string | undefined;
    id: number | undefined;
    firstName: string | undefined;
    lastName: string | undefined;
    email: string | undefined;
    roles: Array<string> | undefined;
}

import { computed, Signal, signal } from "@angular/core";
import { JWTHeader } from "./jwt-header";
import { JWTPayload } from "./jwt-payload";

export class Jwt {

    public readonly token: Signal<string>;
    
    private readonly splitToken: Signal<string[]> = computed(() => {
        const parts = this.token().split('.');
        if (!parts || parts.length !== 3) {
            throw new Error('Invalid JWT token format');
        }
        return parts;
    });
    
    public readonly encodedHeader: Signal<string> = computed(
        () => this.splitToken()[0]
    );
    
    public readonly encodedPayload: Signal<string> = computed(
        () => this.splitToken()[1]
    );
    
    public readonly signature: Signal<string> = computed(
        () => this.splitToken()[2]
    );

    public readonly header: Signal<JWTHeader> = computed(() => {
        return JSON.parse(atob(this.encodedHeader())) as JWTHeader;
    });

    public readonly payload: Signal<JWTPayload> = computed(() => {
        return JSON.parse(atob(this.encodedPayload())) as JWTPayload;
    });

    constructor(token: string) {
        if (!token) {
            throw new Error('Token cannot be empty or null or undefined');
        }
        this.token = signal(token);
    }

}

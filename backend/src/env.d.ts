declare namespace NodeJs {
    interface ProcessEnv {
        DB_USER: string;
        DB_PASSWORD: string;
        DB_HOST: string;
        DB_PORT: number;
        DB_NAME: string;
    }
}
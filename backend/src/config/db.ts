import { Pool } from 'pg';
import dotenv from 'dotenv';
import { connect } from 'http2';

export default async function connectDB() {
    dotenv.config();

    if (global.connection) {
        return global.connection.connect();
    }

    const pool = new Pool({
        connectionString: process.env.CONNECTION_STRING
    });

    const client = await pool.connect();
    
    console.log('Conectado ao banco de dados');
    const res = await client.query('SELECT NOW()');
    console.log(res.rows[0]);

    client.release();

    global.connection = pool;
    return pool.connect();
}
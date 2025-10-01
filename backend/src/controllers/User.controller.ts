import { Request, RequestHandler, Response } from "express";
import db from "../config/db.ts";
import createToken from "../core/token.ts";
import { comparePassword, hashPassword } from "../core/hash.ts";

class UserController {
    static newUser: RequestHandler = async (req: Request, res: Response) => {
        const {
            nome,
            nascimento,
            genero,
            telefone,
            email,
            senha
        } = req.body;

        if (
            !nome ||
            !nascimento ||
            !genero ||
            !telefone ||
            !email ||
            !senha
        ) {
            res.sendStatus(400);
        }

        const user = await db();

        try {
            await user.query(`
                INSERT INTO Usuarios (
                    email, nome, data_nascimento, genero, telefone, senha, user_token
                ) VALUES (
                    $1, $2, $3, $4, $5, $6, $7
                )
            `, [email, nome, nascimento, genero, telefone, await hashPassword(senha), createToken()]);
        } catch (error) {
            console.log(error);
            res.sendStatus(500);
        } finally {
            user.release();
        }

        res.sendStatus(201);

    }

    static loginUser: RequestHandler = async (req: Request, res: Response) => {
        const { email, senha } = req.body;

        if (!email || !senha) {
            res.sendStatus(400);
        }

        const user = await db();

        try {
            const result = await user.query(`
                SELECT * FROM Usuarios WHERE email = $1
            `, [email]);

            if (result.rows.length === 0) {
                res.sendStatus(401);
                return;
            }

            const userResult = result.rows[0];

            const isValidPassword = await comparePassword(senha, userResult.senha);

            if (!isValidPassword) {
                res.sendStatus(401);
                return;
            }

            res.status(200).json({
                token: userResult.user_token
            });

        } catch (error) {
            console.log(error);
            res.sendStatus(500);
        } finally {
            user.release();
        }
    }

    static getUser: RequestHandler = async (req: Request, res: Response) => {
        const { id } = req.params;
        if (!id) {
            res.sendStatus(400);
        }

        const user = await db();

        try {
            const result = await user.query(`
                SELECT * FROM Usuarios WHERE user_token = $1
            `, [id]);

            if (result.rows.length === 0) {
                res.sendStatus(401);
                return;
            }

            res.status(200).json({
                nome: result.rows[0].nome,
                email: result.rows[0].email
            });
        } catch (error) {
            console.log(error);
            res.sendStatus(500);
        } finally {
            user.release();
        }
    }
}

export default UserController;
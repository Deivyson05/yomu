import { Request, Response } from "express";
import db from "../config/db.ts";
import createToken from "../core/token.ts";
import { comparePassword, hashPassword } from "../core/hash.ts";

class AdopterController {
    async createAdopter(req: Request, res: Response) {
        const {
            about,
            address,
            recearch,
            preference,
            login
        } = req.body;

        if (
            !about ||
            !address ||
            !recearch ||
            !preference ||
            !login
        ) {
            return res.sendStatus(400);
        }


        try {
            const client = await db();
            await client.query(`
            INSERT INTO Usuarios (
                user_id,
                tipo_id,
                user_nome,
                user_telefone,
                user_email,
                user_senha,
                user_token,
                user_img_url
            ) VALUES (
                $1, $2, $3, $4, $5, $6, $7, $8
            )
            `, [
                about.cpf,
                1,
                about.name,
                about.phone,
                login.email,
                await hashPassword(login.password),
                createToken(),
                'https://imgs.search.brave.com/awksT_Zoh8G9Qn5d-CbZP4gAPcl0EDxLP0J88fgAnB4/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvNTg3/ODA1MTU2L3ZlY3Rv/ci9wcm9maWxlLXBp/Y3R1cmUtdmVjdG9y/LWlsbHVzdHJhdGlv/bi5qcGc_cz02MTJ4/NjEyJnc9MCZrPTIw/JmM9Z2t2TERDZ3NI/SC04SGVRZTdKc2po/bE9ZNnZSQkprX3NL/VzlseWFMZ21Mbz0'
            ]);

            await client.query(`
                INSERT INTO Adotante (
                    user_id,
                    adt_pontuacao
                ) VALUES (
                    $1, $2
                )
                
            `, [about.cpf, 0]);

            await client.query(`
                INSERT INTO Endereco (
                    user_id,
                    end_cep,
                    end_estado,
                    end_cidade,
                    end_bairro,
                    end_rua,
                    end_numero,
                    end_complemento
                ) VALUES (
                    $1, $2, $3, $4, $5, $6, $7, $8
                )
            `, [
                about.cpf,
                address.cep,
                address.state,
                address.city,
                address.neighborhood,
                address.street,
                address.number,
                address.complement
            ]);

            const adopterId = await client.query(`
                SELECT adt_id FROM Adotante WHERE user_id = $1
            `, [about.cpf]);

            await client.query(`
                INSERT INTO Pesquisa (
                    adt_id,
                    pesq_espaco_para_pet,
                    pesq_trab_fora,
                    pesq_tem_outros_pets,
                    pesq_aceita_visitas,
                    pesq_tempo_pet_sozinho,
                    pesq_experiencia_com_pets
                ) VALUES (
                    $1, $2, $3, $4, $5, $6, $7
                )
            `, [
                adopterId.rows[0].adt_id,
                recearch.petSpace,
                recearch.worksOutside,
                recearch.haveOtherPets,
                recearch.acceptsVisits,
                recearch.petAloneTime,
                recearch.experienceWithPets
            ]);

            await client.query(`
                INSERT INTO Preferencia (
                    adt_id,
                    pref_especie,
                    pref_porte,
                    pref_idade,
                    pref_sexo,
                    pref_temperamento,
                    pref_aceita_pets_pcd
                ) VALUES (
                    $1, $2, $3, $4, $5, $6, $7
                )
            `, [
                adopterId.rows[0].adt_id,
                preference.species,
                preference.size,
                preference.age,
                preference.gender,
                preference.temperament,
                preference.acceptsPetsWithDeficiencies
            ]);


        } catch (error) {
            console.log(error);
            return res.sendStatus(500);
        }

        return res.sendStatus(200);
    }
}

export default new AdopterController();
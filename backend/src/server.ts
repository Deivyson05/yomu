import express from 'express';
import cors from 'cors';
import db from './config/db.ts';
import router from './routes/routes.ts';
const app: express.Application = express();

app.use(cors());
app.use(express.json());

const port = process.env.PORT || 3001;

app.use(router);

db();

app.listen(port, () => {
    console.log('Server is running in http://localhost:3001');
});
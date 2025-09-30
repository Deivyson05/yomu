import { Router } from "express";
import UserController from "../controllers/User.controller";

const router:Router = Router();

router.post('/new', UserController.newUser);
router.post('/login', UserController.loginUser);

export default router;
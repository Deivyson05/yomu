export default function createToken() {
    const carachters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let a = '';
    let b = '';
    let c = '';
    for (let i = 0; i < 20; i++) {
        a += carachters.charAt(Math.floor(Math.random() * carachters.length));
        b += carachters.charAt(Math.floor(Math.random() * carachters.length));
        c += carachters.charAt(Math.floor(Math.random() * carachters.length));
    }

    let token = `${a}-${b}-${c}`
    return token;
}
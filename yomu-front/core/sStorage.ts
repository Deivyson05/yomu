'use client'

export function setSessionData(key: string, value: any) {
    sessionStorage.setItem(key, JSON.stringify(value));
}

export function getSessionData(key: string) {
    const data = sessionStorage.getItem(key);
    return data ? JSON.parse(data) : null;
}

export function updateSectionData(key: string, key2: string, value: any) {
    let obj = getSessionData(key);
    if(key2 in obj) {
        obj[key2] = value;
        setSessionData(key, obj);
    }
}
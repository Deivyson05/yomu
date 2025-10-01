'use client'

export function setData(key: string, value: any) {
    localStorage.setItem(key, JSON.stringify(value));
}

export function getData(key: string) {
    const data = localStorage.getItem(key);
    return data ? JSON.parse(data) : null;
}

export function updateData(key: string, key2: string, value: any) {
    const obj = getData(key);
    if (obj && key2 in obj) {
        obj[key2] = value;
        setData(key, obj);
    }
}
const http = uni.$u.http

export const getAllConfig = () => http.get('/config/getAll')
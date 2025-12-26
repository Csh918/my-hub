const http = uni.$u.http

export const getLatestNotice = () => http.get('/notice/getLatest')
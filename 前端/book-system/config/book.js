const http = uni.$u.http

export const getLatestBook = (currentPage,pageSize) => http.get(`/book/getLatest/${currentPage}/${pageSize}`)
export const getBookByCid = (cid) => http.get(`/book/getByCid/${cid}`)
export const getBookById = (id) => http.get(`/book/getById/${id}`) 
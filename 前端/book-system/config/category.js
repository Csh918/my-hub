const http = uni.$u.http

export const getAllCategory = () => http.get('/category/getAll')

export const getAllCategoryAndBook = () => http.get(`category/getAllAndBook`)

import { data } from "uview-ui/libs/mixin/mixin"

const http = uni.$u.http

export const login = (data) => http.post(`/api/wx/login`,data)

export const uploadAvatarUrl = (params) => http.post('/api/wx/upload',params)
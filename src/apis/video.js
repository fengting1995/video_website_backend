import request from './request'

async function getTypes(){
    const url ='/type/getAll'
    return request.get(url)
}


async function getVideoById(av) {
    const url = '/video/detail'
    return request.get(url, {params: {av: av}})
}

async function getVideoList(params){
    const url = '/video/pageByQuery'
    return request.get(url,{params})
}

async function getVideoByMultipleAttributes(params){
    const url = '/video/getVideoByMultipleAttributes'
    return request.get(url,{params})
}



export {getTypes,getVideoById,getVideoList,getVideoByMultipleAttributes}
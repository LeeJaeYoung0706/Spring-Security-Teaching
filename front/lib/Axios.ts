import axios, {AxiosInstance, AxiosResponse} from "axios";
import {AxiosParameter} from "@/lib/AxiosParameter";

export const instance: AxiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_APP_URL,
  timeout: 1000 * 10,
  validateStatus: function (status) {
    return status >= 200 && status < 300;
  }
})

const apiConfigSetting: (
  method: string,
  AxiosParameter: APIProps,
  instance: AxiosInstance
) => Promise<AxiosResponse<any>> = (method: string, AxiosParameter: APIProps , instance: AxiosInstance) => {
  const { url, param, multipartUse } = AxiosParameter;
  const config: any = {
    url: url, // + '?lang=' + nowLanguage()
    method: method,
  };

  multipartUse
    ? (config.headers = {
      'Content-Type': 'multipart/form-data; charset=utf-8',
    })
    : (config.headers = {
      'Content-Type': 'application/json; charset=utf-8',
    });
  method === 'get' || method === 'delete'
    ? (config.params = param)
    : (config.data = param);

  return instance(config);
}

export const Axios = {
  get: (AxiosParameter: APIProps) => {
    return apiConfigSetting('get', AxiosParameter, instance);
  },
  post: (AxiosParameter: APIProps) => {
    return apiConfigSetting('post', AxiosParameter, instance);
  },
  patch: (AxiosParameter: APIProps) => {
    return apiConfigSetting('patch', AxiosParameter, instance);
  },
  delete: (AxiosParameter: APIProps) => {
    return apiConfigSetting('delete', AxiosParameter, instance);
  }
}
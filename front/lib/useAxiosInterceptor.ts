import {AxiosResponse} from "axios";
import {instance} from "@/lib/Axios";


const apiSuccessHandler = (response: AxiosResponse) => {
  // console.log(response);
  if(response.data.code !== 200) {
    alert(response.data.code);
  }
  return response.data.data;
}

const apiFailHandler = (error: any) => {
  //return error?.message;
  return error?.message;
}

const useAxiosInterceptor = () => {
  instance.interceptors.request.use(
    async (config: any) => {
      const token = localStorage.getItem("token");
      console.log(" ####")
      if(token) {
        config.headers[`Authorization`] = `${token}`; // for Spring Boot back-end
      }
      return config;
    },
    async (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    async (response) => {
      return response
    },
    async (error) => {
      return error
    }
  );
}

export default useAxiosInterceptor;
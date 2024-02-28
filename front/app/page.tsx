'use client'
import style from '@/app/login.module.css';
import {useCallback, useState} from "react";
import useAxiosInterceptor from "@/lib/useAxiosInterceptor";
import {Axios} from "@/lib/Axios";
import {AxiosParameter} from "@/lib/AxiosParameter";

interface loginDataInterface {
  id: string;
  password: string;
}

export default function Home() {

  useAxiosInterceptor();

  const initLoginData: loginDataInterface = {
    id: "",
    password: ""
  }
  const [loginState , setLoginState] = useState<loginDataInterface>(initLoginData);

  const [isLogin , setIsLogin] = useState<boolean>(false);
  const loginButtonClick = ()=> {

    const formData = new FormData();
    formData.append('userId',loginState.id);
    formData.append('password',loginState.password);

    const api =
      new AxiosParameter.Builder().setUrl("/auth/signin").setMultipartUse(false).setParam(formData).build();
    Axios.post(api).then((result) => {
       const auth = result?.data?.auth;
       const token = result?.data?.token;
       const authArray = result?.data?.grantedAuth;
       if (token) {
         localStorage.setItem("token" , token);
         localStorage.setItem("auth" , auth);
         localStorage.setItem("authArray" , authArray);
         setIsLogin(() => true);
       } else {
         localStorage.clear();
         setIsLogin(() => false);
       }
    }).catch(()=> {
      localStorage.clear();
      setIsLogin(() => false);
    });

  }

  const valueStateSetting = useCallback( (kind : string) => (e : React.ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.value)
    if (kind === "id") {
        setLoginState( (pre) => {
          return {
            ...pre,
            id: e.target.value
          }
        })
    } else if (kind === "password") {
      setLoginState( (pre) => {
        return {
          ...pre,
          password:  e.target.value
        }
      })
    }
  }, [])


  const logoutOnClickHandler = () => {
    localStorage.clear();
  }

  const noticeRequestOnClickHandler = () => {
    const api =
      new AxiosParameter
        .Builder()
        .setUrl("/test/notice")
        .setMultipartUse(false)
        .setParam(null).build();
    Axios.get(api).then((result) => {
      console.log(result)
    });
  }

  const managerRequestOnClickHandler = () => {
    const api =
      new AxiosParameter
        .Builder()
        .setUrl("/test/board")
        .setMultipartUse(false)
        .setParam(null).build();
    Axios.get(api).then((result) => {
      console.log(result)
    });
  }

  const popupRequestOnClickHandler = () => {
    const api =
      new AxiosParameter
        .Builder()
        .setUrl("/test/popup")
        .setMultipartUse(false)
        .setParam(null).build();
    Axios.get(api).then((result) => {
      console.log(result)
    });
  }

  return (
    <div className={style.container}>
      <div className={style.form_div}>
        <h1 className={style.login_title}>Login</h1>
        <form>
          <div className={style.login_div}>
            <div className={style.inputs_div}>
              <div className={style.input_div}>
                <p className={style.login_p}>아이디</p>
                <input type={"text"} value={loginState.id} onChange={(e) => valueStateSetting("id")(e) }/>
              </div>
              <div className={style.input_div}>
                <p className={style.login_p}>비밀번호</p>
                <input type={"password"} value={loginState.password} onChange={(e) => valueStateSetting("password")(e) }/>
              </div>
            </div>
            <button  className={style.button}
                     type={"button"}
                     onClick={loginButtonClick}>
              <span>
                Login
              </span>
            </button>
          </div>
        </form>
      </div>
      <div className={style.test_button_list}>
        <h1 className={style.login_title}>요청 테스트</h1>
        <button  className={style.button}
                 type={"button"}
                 onClick={noticeRequestOnClickHandler}>
              <span>
                공지사항 기능 접근 가능
              </span>
        </button>
        <button  className={style.button}
                 type={"button"}
                 onClick={managerRequestOnClickHandler}>
              <span>
                게시판 기능 접근 가능
              </span>
        </button>
        <button  className={style.button}
                 type={"button"}
                 onClick={popupRequestOnClickHandler}>
              <span>
                팝업 기능 접근 가능
              </span>
        </button>
        <button  className={style.button}
                 type={"button"}
                 onClick={logoutOnClickHandler}>
              <span>
                Logout
              </span>
        </button>
      </div>
    </div>
  );
}

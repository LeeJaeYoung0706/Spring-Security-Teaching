
interface axiosParamInterface {
  [key in string]: string | object;
}

interface APIProps {
  url: string;
  param?: axiosParamInterface | null;
  multipartUse?: boolean;
}


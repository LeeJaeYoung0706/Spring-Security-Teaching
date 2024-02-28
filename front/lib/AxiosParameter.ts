

export class AxiosParameter implements APIProps{
  public url: string;
  public param: axiosParamInterface | null;
  public multipartUse: boolean;

  private constructor(
    url: string,
    param: axiosParamInterface | null,
    multipartUse: boolean
  ) {
    this.param = param;
    this.url = url;
    this.multipartUse = multipartUse;
  }

  public static Builder = class {
    private _url = '';
    private _param = null;
    private _multipartUse = false;

    setUrl(value: string) {
      this._url = value;
      return this;
    }

    setParam(value: any) {
      this._param = value;
      return this;
    }

    setMultipartUse(value: boolean) {
      this._multipartUse = value;
      return this;
    }

    build() {
      return Object.freeze(new AxiosParameter(this._url, this._param, this._multipartUse));
    }

  };
}
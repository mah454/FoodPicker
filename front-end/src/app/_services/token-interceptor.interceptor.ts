import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "./Auth.service";

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {
  constructor(private api: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let token = this.api.getToken();
    let tokenizeRequest = request.clone({
      setHeaders: { authorization: `bearer ${token}` },
    });
    return next.handle(tokenizeRequest);
  }
}

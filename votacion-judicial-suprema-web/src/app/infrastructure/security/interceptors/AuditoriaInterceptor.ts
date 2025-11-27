import { HttpInterceptorFn } from "@angular/common/http";
import { tap } from "rxjs";

export const auditoriaInterceptor: HttpInterceptorFn = (req, next) => {
    if (!req.url.endsWith('api/authenticate')) {
        //console.log("interceptor auditoria");
        req = req.clone({
            setHeaders: {
                'X-Request-Usuario-Aplicativo':  'USUARIO',
                'X-Request-Usuario-Red': `usuario`,
                'X-Request-Ip': `172.34.12.72`,
                'X-Request-Pc': `pc-usuario`,
                'X-Request-Mac': `00:00:00:00:00:00`
            }
        });
    }
    return next(req).pipe(
        tap(resp => resp)
    );
    //return next(req);
}
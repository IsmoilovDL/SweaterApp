<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> User Name :  </label>
            <input type="text" name="username" class="form-control col-md-4 ${(usernameError??)?string('is-invalid','')}"
                   placeholder="User name" value="<#if user??>${user.username}</#if>"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> Password: </label>
            <input type="password" name="password" class="form-control col-md-4 ${(passwordError??)?string('is-invalid','')}" placeholder="password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> Password retype: </label>
            <input type="password" name="password2" class="form-control col-md-4 ${(password2Error??)?string('is-invalid','')}" placeholder="password retype"/>
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> Email: </label>
            <input type="email" name="email" class="form-control col-md-4 ${(emailError??)?string('is-invalid','')}" placeholder="some@some.com"/>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
        <div class="col-md-6">
            <div class="g-recaptcha" data-sitekey="6LcxrqMfAAAAAARtBUOpdHQ0_HPgjesSeKQYmVSS"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                    ${captchaError}
                </div>
            </#if>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="form-group row">
            <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign In</#if></button>
        </div>
    </form>
    <div>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
    </div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="<#if user??>Sign Out<#else>Log In</#if>" class="btn btn-primary"/>
    </form>
</#macro>
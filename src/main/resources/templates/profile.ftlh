<#import "parts/common.ftlh" as c>
<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}

    <form method="post">

        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> Password: </label>
            <input type="password" name="password" class="form-control col-md-4" placeholder="password"/>
        </div>
            <div class="form-group row">
                <label class="form-text text-muted col-md-2"> Email: </label>
                <input type="email" name="email" class="form-control col-md-4" placeholder="some@some.com" value="${email!''}"/>
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="form-group row">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </form>

</@c.page>

var helloit = helloit || {};

helloit.createNavbar = function () {

    return $('\
    <nav class="navbar navbar-light bg-faded main-navbar">\
        <button class="navbar-toggler hidden-sm-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar2">\
            &#9776;\
        </button>\
        <div class="collapse navbar-toggleable-xs" id="exCollapsingNavbar2">\
            <a class="navbar-brand" href="#">Household tracker</a>\
            <ul class="nav navbar-nav">\
                <li class="nav-item">\
                    <a class="nav-link" href="index.html">Home</a>\
                </li>\
                <li class="nav-item">\
                    <a class="nav-link" href="expense.html">Expense</a>\
                </li>\
                <li class="nav-item pull-xs-right">\
                    <a class="nav-link" href="account/logout">Logout</a>\
                </li>\
            </ul>\
        </div>\
    </nav>');
};


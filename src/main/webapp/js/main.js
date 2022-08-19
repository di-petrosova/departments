var ACC = ACC || {};

ACC.showemployees = {
    _autoload: ["bindShowEmployeeButton"],
    bindShowEmployeeButton: function () {
        $(".showEmployees").click(function () {
            let $currentButton = $(this);
            if ($currentButton.parent().parent().next().hasClass("addedEmployees")) {
                $currentButton.parent().parent().next(".addedEmployees").remove();
            } else {
                let depId = $(this).parent().parent().find('.departmentId').text();
                $.ajax({
                    type: "GET",
                    url: "/newdep/employees/departments_employees",
                    context: this,
                    dataType: 'json',
                    data: {
                        id: depId
                    },
                    success: function (data) {
                        ACC.showemployees.renderEmployees(data, $currentButton);
                    },
                    error: function () {
                        alert('error');
                    }
                });
            }
        })
    },
    renderEmployees: function (data, $clickedButton) {
        $clickedButton.parent().parent().after("<tr class='addedEmployees'><td colspan='6'><table class='employees'></table></td></tr>");
        if (data.length === 0) {
            $clickedButton.parent().parent().next().find("table").append("There are no employees for this department");
        } else {
            Object.keys(data).forEach(function (element) {
                $clickedButton.parent().parent().next().find("table").append("<tr class='employee'>" +
                    "<td>" + (parseInt(element) + 1) + "</td>" +
                    "<td>Name: " + data[element].firstName + "</td>" +
                    "<td>Last Name: " + data[element].lastName + "</td>" +
                    "<td>Email: " + data[element].email + "</td>" +
                    "</tr>")
            });
        }
    }
}

function _autoload() {
    $.each(ACC, function (section, obj) {
        if ($.isArray(obj._autoload)) {
            $.each(obj._autoload, function (key, value) {
                if ($.isArray(value)) {
                    if (value[1]) {
                        ACC[section][value[0]]();
                    } else {
                        if (value[2]) {
                            ACC[section][value[2]]()
                        }
                    }
                } else {
                    ACC[section][value]();
                }
            })
        }
    })
}

$(function () {
    _autoload();
});


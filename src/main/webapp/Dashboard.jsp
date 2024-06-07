<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <title>RLS Management</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <form id="dashboardForm">
            <div class="form-group">
                <p>PF</p>
                 <select id="pfid-container" style="width: 50%;" multiple="multiple" name="PFID"></select>
            
                <br>
                <p>Dashboard Name</p>
                <select class="form-control form-control-sm" name="dashboardName" id="dashboardName" multiple="multiple">
                </select>
                <p class="mt-4">Access Type</p>
                <select class="form-control form-control-sm" name="accessType" id="accessType">
                    <option value="G">Grant</option>
                    <option value="R">Revoke</option>
                </select>
            </div>
            <button type="button" class="btn btn-primary" id="submitBtn">Submit</button>
        </form>
    </div>
    
    <script>
        $(document).ready(function() {
        	$('#pfid-container').select2({
                tags: true,
                tokenSeparators: [',', ' ']
            });
            // Initialize select2 for dashboardName
            $('#dashboardName').select2();

            // Function to add another PFID input field
            $('#addPFID').click(function() {
                $('#pfid-container').append('<input type="text" class="form-control mb-2" name="PFID">');
            });

            loadDbNames();

            $('#submitBtn').click(function() {
                var selectedAccessType = $('#accessType').val();
                if (selectedAccessType === 'G') {
                    grantAccess();
                } else {
                    revokeAccess();
                }
            });

            function grantAccess() {
                $.ajax({
                    url: '<%= request.getContextPath() %>/save',
                    method: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
                    data: $('#dashboardForm').serialize(),
                    processData: false,
                    success: function(response) {
                        console.log(response);
                    },
                    error: function(xhr, status, error) {
                        console.error(error);
                    }
                });
            }

            function revokeAccess() {
                $.ajax({
                    url: '<%= request.getContextPath() %>/Revoke',
                    method: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
                    data: $('#dashboardForm').serialize(),
                    processData: false,
                    success: function(response) {
                        console.log("revoke called");
                    },
                });
            }

            function loadDbNames() {
                $.ajax({
                    url: '<%= request.getContextPath() %>/fetchDashboardDetails',
                    method: 'POST',
                    contentType: 'application/x-www-form-urlencoded',
                    success: function(response) {
                        $.each(response, function(index, dashboard) {
                            $('#dashboardName').append(
                                $('<option>', {
                                    value: dashboard.dashboardKey,
                                    text: dashboard.dashboardName
                                })
                            );
                        });
                    },
                });
            }
        });
    </script>
</body>
</html>

<#-- @ftlvariable name="events" type="java.util.Collection<com.atlassian.build.common.Entry>" -->
<!doctype html>
<html>
<head>
    <title>Build Time Breakdown</title>


    <link rel="stylesheet" type="text/css" href="http://visapi-gadgets.googlecode.com/svn/trunk/termcloud/tc.css"/>
    <script type="text/javascript" src="http://visapi-gadgets.googlecode.com/svn/trunk/termcloud/tc.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <script type="text/javascript">

        function processData(data) {
            // Load the Visualization API and the piechart package.
            google.load('visualization', '1', {'packages':['piechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.setOnLoadCallback(drawChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart() {

                // Create our data table.

                var dataTable = new google.visualization.DataTable(data.tasks, 0.6);

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('tasks'));
                chart.draw(dataTable, {width: 600, height: 400, is3D: true, title: 'Task Times'});

                var targetDataTable = new google.visualization.DataTable(data.targets, 0.6);
                var targetchart = new google.visualization.PieChart(document.getElementById('targets'));
                targetchart.draw(targetDataTable, {width: 600, height: 400, is3D: true, title: 'Target Times'});


                var taskTC = new TermCloud(document.getElementById('tasksCloud'));
                taskTC.draw(dataTable, null);

                var targetTC = new TermCloud(document.getElementById('targetsCloud'));
                targetTC.draw(targetDataTable, null);

            }
        }

        google.load("jquery", "1.4.2");


    </script>
    <script type="text/javascript" src="data.js"></script>
    <style type="text/css">
        ol {
            font-size: 10px;
        }

        table.blogstats span {
            position: relative;
            z-index: 5;
        }

        td.blogstattitle table.blogstats td {
            color: #969696;
            font-size: 12px;
        }

        table.blogstats td {
            color: #969696;
            font-size: 12px;
        }
    </style>

</head>
<body onload="">
<h1>Build Breakdown</h1>

<div>
    <div id="tasks"></div>
    <div id="tasksCloud"></div>
</div>
<div id="targets"></div>
<div id="targetsCloud"></div>

<div id="eventview">
    <h2>Event Log</h2>

    <table class="blogstats">
    <tbody>
    <#list events as x>
       
        <tr>
            <td class="blogstatstitle" width="500px">
                <div>
                    <span class="contentTitle" style="font-size:14px; color:"> ${x.entryName!""} - ${x.durationStr} (ms)</span>
                    <div class="percentageBar" style="width: ${x.pcStr}%; background-color:#CCCCCC; padding:4px; -moz-border-radius-bottomright:6px; -webkit-border-bottom-right-radius:6px; -moz-border-radius-topright:6px; -webkit-border-top-right-radius:6px;">&nbsp;</div>
                </div>
            </td>
        </tr>

    </#list>
    </tbody>
    </table>

</div>

</body>
</html>

        <!--style=" "-->
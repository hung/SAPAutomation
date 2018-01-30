<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Check SAP ${SID} on ${today}</title>
    
    <style>
    .report-box{
        max-width:90%;
        margin:auto;
        padding:30px;
        border:1px solid #eee;
        box-shadow:0 0 10px rgba(0, 0, 0, .15);
        font-size:16px;
        line-height:24px;
        font-family:'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;
        color:#555;
    }
    
    .report-box table{
        width:100%;
        line-height:inherit;
        text-align:left;
    }
    
    .report-box table td{
        padding:5px;
        vertical-align:top;
    }
    
    .report-box table tr td:nth-child(2){
        text-align:right;
    }
    
    .report-box table tr.top table td{
        padding-bottom:20px;
    }
    
    .report-box table tr.top table td.title{
        font-size:45px;
        line-height:45px;
        color:#333;
    }
    
    .report-box table tr.information table td{
        padding-bottom:40px;
    }
    
    .report-box table tr.heading td{
        background:#eee;
        border-bottom:1px solid #ddd;
        font-weight:bold;
    }
    
    .report-box table tr.details td{
        padding-bottom:20px;
    }
    
    .report-box table tr.item td{
        border-bottom:1px solid #eee;
    }
    
    .report-box table tr.item.last td{
        border-bottom:none;
    }
    
    .report-box table tr.total td:nth-child(2){
        border-top:2px solid #eee;
        font-weight:bold;
    }
    
    @media only screen and (max-width: 600px) {
        .report-box table tr.top table td{
            width:100%;
            display:block;
            text-align:center;
        }
        
        .report-box table tr.information table td{
            width:100%;
            display:block;
            text-align:center;
        }
    }
    </style>
</head>

<body>
    <div class="report-box">
        <table cellpadding="0" cellspacing="0">
            <tr class="top">
                <td>
                    <h1>Report check <b>${SID}</b> on <b>${today}</b> for customer <b>${customer}</b></h1>
                </td>
            </tr>		
            <#list myData?keys as key> 
			<tr>
				<td>
				<h3>${key_index + 1}. ${key}</h3>
				<#if myData[key]?has_content>
					<#list myData[key] as imgs>
						<div align="center"><img src="${today}/${imgs}" width="80%" height="80%"></div><br/>
					</#list>
				<#else>
					Cannot capture Image / Please re-check this TCODE
				</#if>
				</td>
			</tr>
			</#list>
            
        </table>
    </div>
</body>
</html>
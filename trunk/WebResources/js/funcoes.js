function Mascara(strNumero)
{		
		var strSemMascara;
		var numContador;
		var numero;

		strSemMascara = strNumero
		TamDig = strSemMascara.length
		numContador = 0;
		numero = "";
          for (i = TamDig; (i >= 0); i--){
              if ((parseInt(strSemMascara.substr(i,1))>=0) && (parseInt(strSemMascara.substr(i, 1))<=9))
                {
                 numContador++;
                 if ((numContador == 3) && ((TamDig -i) < 5))
                  {numero = "."+numero;
                   numContador = 0;
                   }
                 else if (numContador == 3)
                  {numero = ","+numero;
                   numContador = 0;
                  }
                 numero = strSemMascara.substr(i, 1)+numero;
                }
               }
		
		TamDig = numero.length;
		//remove os zeros a esquerda
		for (i=TamDig-1;(i>=0);i--)
		{
			if (((numero.substr(0,1) == "0")||(numero.substr(0,1) == "."))&&(numero.length!=4))
			{
				numero= numero.substr(1,numero.length-1);
			}
			else
			{
				break
			}
		}			
			
	return(numero)
}
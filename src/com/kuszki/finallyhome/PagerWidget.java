package com.kuszki.finallyhome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*! \brief Klasa implementująca widget pagera.
 * 
 *  Stanowi bazę dla klasy ClientCore.
 * 
 */ public class PagerWidget extends FragmentPagerAdapter
{
	
	protected static final int count = 3;	//!< Liczba stron w pagerze.
	
/*! \brief Konstruktor.
 *  \param [in] fm Używany menager fragmentów.
 * 
 *  Inicjuje widget.
 * 
 */ public PagerWidget(FragmentManager fm)
	{
        super(fm);
    }

/*! \brief Metoda zwracająca wybraną stronę.
 *  \param [in] pos Numer strony.
 * 
 *  Tworzy instancję wybranej strony na podstawie klasy ViewPage.
 * 
 */ @Override public Fragment getItem(int pos)
    {
    	return new ViewPage(pos);
    }

/*! \brief Zwraca ilość stron.
 * 
 *  Informuję o ilości stron na podstawie zmiennej PagerWidget::count.
 * 
 */ @Override public int getCount()
    {
        return count;
    }

}
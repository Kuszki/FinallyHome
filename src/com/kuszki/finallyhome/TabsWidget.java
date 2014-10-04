/*! \file
    \brief Plik zawierający implementacje klasy TabsWidget.
*/

package com.kuszki.finallyhome;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.view.ViewPager;

/*! \brief Klasa implementująca widget zakładek.
 * 
 *  Klasa używana do przełączania stron za pomocą zakładek.
 * 
 */
public class TabsWidget implements ActionBar.TabListener {

	private final ViewPager pager;	//!< Instancja pagera.
	
	/*! \brief Konstruktor.
	 *  \param [in] viewPager Pager zarządzający stronami.
	 * 
	 *  Inicjuje pole TabsWidget::pager.
	 * 
	 */
	public TabsWidget(ViewPager viewPager)
	{
		pager = viewPager;
	}
	

	/*! \brief Zdarzenie wywoływane przy zaznaczeniu zakładki.
	 *  \param [in] tab Instancja zakładki.
	 *  \param [in] frg Instancja fragmentu.
	 * 
	 *  Zmienia aktualną zakładkę gdy zmieniona zostanie strona.
	 * 
	 */
	@Override public void onTabSelected(Tab tab, android.app.FragmentTransaction frg)
	{
		
		pager.setCurrentItem(tab.getPosition());
		
	}

	/*! \brief Zdarzenie wywoływane przy odznaczeniu zakładki.
	 *  \param [in] tab Instancja zakładki.
	 *  \param [in] frg Instancja fragmentu.
	 * 
	 *  Nieużywane.
	 * 
	 */
	@Override public void onTabUnselected(Tab tab, android.app.FragmentTransaction frg) {}
	
	/*! \brief Zdarzenie wywoływane przy ponownym zaznaczeniu zakładki.
	 *  \param [in] tab Instancja zakładki.
	 *  \param [in] frg Instancja fragmentu.
	 * 
	 *  Nieużywane.
	 * 
	 */
	@Override public void onTabReselected(Tab tab, android.app.FragmentTransaction frg) {}
	
}

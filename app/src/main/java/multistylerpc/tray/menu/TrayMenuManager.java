package multistylerpc.tray.menu;

import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import multistylerpc.discord.style.StyleModule;
import multistylerpc.discord.value.Value;
import multistylerpc.discord.value.values.BooleanValue;
import multistylerpc.discord.value.values.FloatValue;
import multistylerpc.discord.value.values.IntegerValue;
import multistylerpc.discord.value.values.ListValue;
import multistylerpc.discord.value.values.LongValue;
import multistylerpc.discord.value.values.TextValue;

public class TrayMenuManager {
    private ArrayList<MenuItem> menus = new ArrayList<>();
    public ArrayList<MenuItem> allMenus() {
        return menus;
    }
    public void createMenu(StyleModule module) {
        menus.clear();
        for (Value<?> v : module.getValues()) 
        {
            if (v instanceof BooleanValue) {
                BooleanValue booleanValue = (BooleanValue) v;
                CheckboxMenuItem cb = new CheckboxMenuItem(booleanValue.getName(), booleanValue.get());
                cb.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        booleanValue.set(cb.getState());
                        System.out.println(String.format("%s has been setted to -> || %s",booleanValue.name, booleanValue.get()));
                    }
                });
                menus.add(cb);
            }else if (v instanceof IntegerValue) {
                IntegerValue integerValue = (IntegerValue) v;
                MenuItem mItem = new MenuItem(integerValue.getName());
                mItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(String.format("Call new Gui + %s", integerValue.name));
                    }
                });
                menus.add(mItem);
            }else if (v instanceof FloatValue) {
                FloatValue floatValue = (FloatValue) v;
                MenuItem mItem = new MenuItem(floatValue.getName());
                mItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(String.format("Call new Gui + %s", floatValue.name));
                    }
                });
                menus.add(mItem);
            }else if (v instanceof LongValue) {
                LongValue longValue = (LongValue) v;
                MenuItem mItem = new MenuItem(longValue.getName());
                mItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(String.format("Call new Gui + %s", longValue.name));
                    }
                });
                menus.add(mItem);
            }else if (v instanceof TextValue) {
                TextValue textValue = (TextValue) v;
                MenuItem mItem = new MenuItem(textValue.getName());
                mItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(String.format("Call new Gui + %s", textValue.name));
                    }
                });
                menus.add(mItem);
            }else if (v instanceof ListValue) {
                ListValue listValue = (ListValue) v;
                Menu menu = new Menu(listValue.getName());
                for (String s : listValue.list) {
                    MenuItem item = new MenuItem(s);
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            listValue.set(s);
                            System.out.println(String.format("%s value has been setted to -> || %s", listValue.name, listValue.value));
                        }
                    });
                    menu.add(item);
                }
                menus.add(menu);
            }
        }
    }
}

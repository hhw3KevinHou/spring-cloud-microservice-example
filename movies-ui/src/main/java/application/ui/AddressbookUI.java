package application.ui;


import application.clients.UserClient;
import application.models.User;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@SpringUI(path = "")
@Title("Addressbook")
@Theme("valo")
public class AddressbookUI extends UI {

    /* Hundreds of widgets.
     * Vaadin's user interface components are just Java objects that encapsulate
     * and handle cross-browser support and client-server communication. The
     * default Vaadin components are in the com.vaadin.ui package and there
     * are over 500 more in vaadin.com/directory.
     */
    TextField filter = new TextField();
    Grid<User> contactList = new Grid<>(User.class);
    Button newContact = new Button("New contact");
    Button refresh = new Button("Refresh");
    // ContactForm is an example of a custom component class
    ContactForm contactForm = new ContactForm();

    // ContactService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    @Autowired
    UserClient userClient;


    /* The "Main method".
     *
     * This is the entry point method executed to initialize and configure
     * the visible user interface. Executed on every browser reload because
     * a new instance is created for each web page loaded.
     */
    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {
         /* Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends
         * only the needed changes to the web page without loading a new page.
         */
        newContact.addClickListener(e -> contactForm.edit(new User(null,"dcd","r3","jjj","ooo", LocalDate.now().toEpochDay())));
       refresh.addClickListener(e ->refreshContacts());
        filter.setPlaceholder("Filter contacts...");
        filter.addBlurListener(e -> refreshContacts(e.toString()));
        
        
        List<User> initList = new ArrayList<User>();
        initList.add(new User(new Long("111"),"ddd","rrr"));
        ListDataProvider<User> dataProviderOBM=  DataProvider.ofCollection(initList);
        contactList.setDataProvider(dataProviderOBM);
       
         contactList.setColumnOrder( "firstName", "lastName", "email");
       // contactList.removeColumn("birthDate");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e  -> {Set<User> set = contactList.getSelectedItems();
                                                                          contactForm.edit(set.iterator().hasNext()?(User)set.iterator().next():null);}
         );
        refreshContacts();
    }

    /* Robust layouts.
     *
     * Layouts are components that contain other components.
     * HorizontalLayout contains TextField and Button. It is wrapped
     * with a Grid into VerticalLayout for the left side of the screen.
     * Allow user to resize the components with a SplitPanel.
     *
     * In addition to programmatically building layout in Java,
     * you may also choose to setup layout declaratively
     * with Vaadin Designer, CSS and HTML.
     */
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newContact,refresh);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        // Split and allow resizing
        setContent(mainLayout);
    }

    /* Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that
     * handle the back-end access and/or the user interface updates.
     * You can further split your code into classes to easier maintenance.
     * With Vaadin you can follow MVC, MVP or any other design pattern
     * you choose.
     */
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        ListDataProvider<User> dataProvider = new ListDataProvider<User>(
                 userClient.findAll().getContent());
        contactList.setDataProvider(dataProvider);
        System.out.println(userClient.findAll().getContent());
         dataProvider.refreshAll();
        contactForm.setVisible(false);
    }


    /*  Deployed as a Servlet or Portlet.
     *
     *  You can specify additional servlet parameters like the URI and UI
     *  class name and turn on production mode when you have finished developing the application.
     */
    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = AddressbookUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


}

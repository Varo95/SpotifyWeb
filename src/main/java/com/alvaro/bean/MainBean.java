package com.alvaro.bean;

import com.alvaro.model.orm.Artist;
import com.alvaro.model.orm.PlayList;
import com.alvaro.service.MainService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Component("mainBean")
@Scope("view")
@Slf4j
public class MainBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter
    private final List<Artist> allArtists;
    @Getter
    private final List<PlayList> allPlayLists;
    @Getter @Setter
    private List<Artist> filteredArtists;
    @Getter @Setter
    private List<PlayList> filteredPlayLists;
    @Getter @Setter
    private Artist selectedArtist;
    @Getter @Setter
    private PlayList selectedPlayList;

    @Autowired
    public MainBean(final MainService mainService) {
        this.allArtists = mainService.getAllArtists();
        this.allPlayLists = mainService.getAllPlayLists();
    }

    public void clearMultiViewState() {
        final String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        PrimeFaces.current().multiViewState().clearAll(viewId, true, this::showMessage);
    }

    private void showMessage(final String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }

}
